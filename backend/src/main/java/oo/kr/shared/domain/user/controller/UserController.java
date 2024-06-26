package oo.kr.shared.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.controller.response.RentalRecordData;
import oo.kr.shared.domain.user.service.UserService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 대여기록 조회 API
   */
  @GetMapping("/rentals/records")
  public ResponseEntity<Page<RentalRecordData>> viewRentalRecord(
      @PageableDefault(sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    Page<RentalRecordData> rentalRecordData = userService.viewRentalRecord(email, pageable);
    return ResponseEntity.ok(rentalRecordData);
  }

  @GetMapping("/me")
  public ResponseEntity<SimpleResponse> checkAuth() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    ResponseType responseType = StringUtils.hasText(email) ? ResponseType.SUCCESS : ResponseType.FAIL;
    return ResponseEntity.ok(new SimpleResponse(responseType));
  }
}