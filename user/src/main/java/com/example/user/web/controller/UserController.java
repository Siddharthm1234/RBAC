package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.dto.requestDto.UserRequestDto;
import com.example.user.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Exposes all User - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUsers() {
        return new ResponseEntity<>(new ResponseDto(userService.getAllUsers(), null), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ResponseDto> getUserByName(@PathVariable String userName) {
        return new ResponseEntity<>(new ResponseDto(userService.getUserByName(userName), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUserByName(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(new ResponseDto(userService.updateUserByName(userRequestDto), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(new ResponseDto(userService.createUser(userRequestDto), null), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userName}")
    public ResponseEntity<ResponseDto> deleteByName(@PathVariable String userName) {

        return new ResponseEntity<>(new ResponseDto(userService.deleteByName(userName), null), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/

    /**
     * This method is an API endpoint used to get a list of groups for a user.
     *
     * @param userName Name of user
     * @return ResponseEntity<ResponseDto>  response entity of global wrapper of all responses.
     */
    @GetMapping("/{userName}/groups")
    public ResponseEntity<ResponseDto> getGroupsByUserName(@PathVariable String userName) {
        return new ResponseEntity<>(new ResponseDto(userService.getGroupsByUserName(userName), null), HttpStatus.OK);
    }

    /*-------------- Check if a User belongs to Group ---------------*/

    /**
     * This method is an API endpoint used to check if a user belongs to a group.
     *
     * @param userName Name of user
     * @param groupId  Id of group
     * @return ResponseEntity response entity with StatusCode 200 OK for true and 404 Not Found for false.
     */
    @GetMapping("/{userName}/groups/{groupId}/check")
    public ResponseEntity<ResponseDto> checkGroupIdForUserName(@PathVariable String userName, @PathVariable Long groupId) {
        return new ResponseEntity<>(new ResponseDto(userService.checkGroupIdForUserName(userName, groupId), null), HttpStatus.OK);
    }

    /*-------------- Check if a User has a Role ---------------*/

    /**
     * This method is an API endpoint used to check if a user has role permission.
     *
     * @param userName Name of user
     * @param roleId   Id of role
     * @return ResponseEntity response entity with StatusCode 200 OK for true and 404 Not Found for false.
     */
    @GetMapping("/{userName}/roles/{roleId}/check")
    public ResponseEntity<?> checkRoleIdForUserName(@PathVariable String userName, @PathVariable Long roleId) {
        return new ResponseEntity<>(new ResponseDto(userService.checkRoleIdForUserName(userName, roleId), null), HttpStatus.OK);
    }


    /**
     * This method is an API endpoint used to get a list of groups and roles for a user.
     *
     * @param userName Name of user
     * @return ResponseEntity<ResponseDto>  response entity of global wrapper of all responses.
     */
    @GetMapping("/{userName}/group-roles")
    public ResponseEntity<ResponseDto> getGroupsAndRolesByUserName(@PathVariable String userName) {
        return new ResponseEntity<>(new ResponseDto(userService.getGroupsAndRolesByUserName(userName), null), HttpStatus.OK);
    }

}
