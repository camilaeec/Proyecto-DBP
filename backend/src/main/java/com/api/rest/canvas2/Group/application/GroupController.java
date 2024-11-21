package com.api.rest.canvas2.Group.application;

import com.api.rest.canvas2.Group.domain.GroupService;
import com.api.rest.canvas2.Group.dto.GroupRequestDto;
import com.api.rest.canvas2.Group.dto.GroupResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupResponseDto createdGroup = groupService.createGroup(groupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<GroupResponseDto>> getGroupsBySection(@PathVariable Long sectionId) {
        List<GroupResponseDto> groups = groupService.getGroupsBySection(sectionId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long groupId) {
        GroupResponseDto group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable Long groupId, @RequestBody GroupRequestDto groupRequestDto) {
        GroupResponseDto updatedGroup = groupService.updateGroup(groupId, groupRequestDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{groupId}/join")
    public ResponseEntity<GroupResponseDto> joinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        GroupResponseDto groupResponseDto = groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok(groupResponseDto);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.leaveGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}
