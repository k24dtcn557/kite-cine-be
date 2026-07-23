package vn.id.hph.kitecine.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.constant.PredefinedRole;
import vn.id.hph.kitecine.controller.param.ChangePasswordParam;
import vn.id.hph.kitecine.controller.param.UserProfileUpdateParam;
import vn.id.hph.kitecine.controller.param.UserRegistrationParam;
import vn.id.hph.kitecine.controller.param.UserSearchParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Role;
import vn.id.hph.kitecine.entity.User;
import vn.id.hph.kitecine.enums.UserStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.facade.dto.UserDto;
import vn.id.hph.kitecine.mapper.UserMapper;
import vn.id.hph.kitecine.repository.RoleRepository;
import vn.id.hph.kitecine.repository.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserDto register(UserRegistrationParam request) {
        User user = userMapper.toUser(request);
        user.setEmail(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE.name());

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserDto(user);
    }

    public UserDto getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserDto(user);
    }

    public UserDto updateProfile(UserProfileUpdateParam param) {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, param);

        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto changePassword(ChangePasswordParam param) {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!param.getNewPassword().equals(param.getConfirmNewPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCHES);
        }

        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_OLD_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(param.getNewPassword()));

        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);

        return userMapper.toUserDto(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setStatus(UserStatus.DELETED.name());
        user.setUsername(user.getUsername() + "_deleted_" + Instant.now().toEpochMilli());
        userRepository.save(user);
    }

    public PageResponse<UserDto> searchUsers(UserSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<User> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();

            if (StringUtils.hasText(param.getKeyword())) {
                String keyword = "%" + param.getKeyword().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), keyword)));
            }

            if (StringUtils.hasText(param.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<User> page = userRepository.findAll(query, pageRequest);
        return PageResponse.<UserDto>builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .data(page.getContent().stream().map(userMapper::toUserDto).toList())
                .build();
    }

    public UserDto getUser(String id) {
        return userMapper.toUserDto(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public String resetUserPassword(User user) {
        String newPwd = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        user.setPassword(passwordEncoder.encode(newPwd));
        userRepository.save(user);
        return newPwd;
    }

    public User get(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public User getByEmail(String email) {
        return userRepository.findByUsername(email).orElse(null);
    }

    public UserDto activateUser(String userId) {
        User user = get(userId);
        user.setStatus(UserStatus.ACTIVE.name());
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto lockUser(String userId) {
        return null;
    }
}
