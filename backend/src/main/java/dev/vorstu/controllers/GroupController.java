package dev.vorstu.controllers;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.services.GroupService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/groups")
@PreAuthorize("hasAuthority('ADMIN')")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @GetMapping
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public GroupDto createGroup(@RequestBody @Valid GroupDto groupDto) {
        return groupService.createGroup(groupDto);
    }

    @PutMapping
    public GroupDto updateGroup(@RequestBody @Valid GroupDto groupDto) {
        return groupService.updateGroup(groupDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
