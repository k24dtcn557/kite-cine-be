package vn.id.hph.kitecine.mapper;

import org.mapstruct.Mapper;
import vn.id.hph.kitecine.entity.FileMgmt;
import vn.id.hph.kitecine.facade.dto.FileMgmtDto;

@Mapper(componentModel = "spring")
public interface FileMgmtMapper {
    FileMgmtDto toFileMgmtDto(FileMgmt fileMgmt);
}
