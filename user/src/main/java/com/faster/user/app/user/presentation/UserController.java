package com.faster.user.app.user.presentation;

import static com.common.resolver.dto.UserRole.ROLE_COMPANY;
import static com.common.resolver.dto.UserRole.ROLE_DELIVERY;
import static com.common.resolver.dto.UserRole.ROLE_HUB;
import static com.common.resolver.dto.UserRole.ROLE_MASTER;

import com.common.aop.annotation.AuthCheck;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import com.faster.user.app.user.facade.UserFacade;
import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserPasswordRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.presentation.dto.response.PDeleteUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetAllUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserFacade userFacade;

  @AuthCheck(roles = {ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<PGetAllUserResponseDto>>> getAllUsers(
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String slackId,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {

    PageResponse<AGetAllUserResponseDto> applicationResponse = userFacade.getAllUsers(username, name, slackId, page,
        size);

    // application DTO → presentation DTO
    PageResponse<PGetAllUserResponseDto> response = applicationResponse.map(PGetAllUserResponseDto::from);

    return ResponseEntity.ok(new ApiResponse<>(
        UserResponseCode.USER_FOUND.getMessage(),
        UserResponseCode.USER_FOUND.getStatus().value(),
        response
    ));
  }

  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse<PDeleteUserResponseDto>> deleteUserRole(@PathVariable(name = "userId") Long userId,
                                                                            @RequestBody PDeleteUserRequestDto requestDto) {

    // presentation DTO → Application DTO
    ADeleteUserRequestDto applicationDto = ADeleteUserRequestDto.from(requestDto);

    ADeleteUserResponseDto applicationResponse = userFacade.deleteUserByUserId(userId, applicationDto);

    // application DTO → presentation DTO
    PDeleteUserResponseDto responseDto = PDeleteUserResponseDto.from(applicationResponse);

    return ResponseEntity
        .status(UserResponseCode.USER_SOFT_DELETED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_SOFT_DELETED.getMessage(),
            UserResponseCode.USER_SOFT_DELETED.getStatus().value(),
            responseDto
        ));
  }

  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @PatchMapping("/{userId}/password")
  public ResponseEntity<ApiResponse<Void>> updateUserPassword(@PathVariable(name = "userId") Long userId,
                                                              @RequestBody PUpdateUserPasswordRequestDto requestDto) {
    AUpdateUserPasswordRequestDto aRequestDto = PUpdateUserPasswordRequestDto.fromDto(requestDto);

    userFacade.updateUserPassword(userId, aRequestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            null
        ));
  }

  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @PatchMapping("/{userId}/slack-id")
  public ResponseEntity<ApiResponse<Void>> updateUserSlackId(@PathVariable(name = "userId") Long userId,
                                                             @RequestBody PUpdateUserSlackIdRequestDto requestDto) {
    AUpdateUserSlackIdRequestDto aRequestDto = PUpdateUserSlackIdRequestDto.from(requestDto);

    userFacade.updateUserSlackId(userId, aRequestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            null
        ));
  }

}