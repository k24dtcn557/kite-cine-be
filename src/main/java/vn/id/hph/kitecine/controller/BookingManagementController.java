package vn.id.hph.kitecine.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.BookingSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.BookingFacade;
import vn.id.hph.kitecine.facade.dto.PurchaseWithShowTimeDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingManagementController {
    BookingFacade bookingFacade;

    @PostMapping("/bookings/search")
    public ApiResponse<PageResponse<PurchaseWithShowTimeDto>> searchBookings(@RequestBody BookingSearchParam param) {
        return ApiResponse.<PageResponse<PurchaseWithShowTimeDto>>builder()
                .result(bookingFacade.searchBookings(param))
                .build();
    }

    @PostMapping("/bookings/{code}/cancel")
    public ApiResponse<Void> cancelBooking(@PathVariable String code) {
        bookingFacade.cancelBooking(code);
        return ApiResponse.<Void>builder().build();
    }
}
