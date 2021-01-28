package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalDuplicated;
import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalNotFound;
import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ourfancyteamname.officespace.db.converters.dtos.UserConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.User_;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.db.repos.UserRoleRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class UserServiceImplTest {

  private static final String SEARCH_TERM = "foo";

  @InjectMocks
  private UserServiceImpl service;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<User> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Mock
  private UserConverter userConverter;

  @Mock
  private UserRoleRepository userRoleRepository;

  @Mock
  private EntityManager entityManager;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRoleListViewServiceImpl userRoleService;

  @Test
  void findAllByPaging() {
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSortingRequest tableSortingRequest = TableSortingRequest.builder()
        .columnName(User_.LAST_NAME)
        .direction(DataBaseDirection.ASC)
        .build();
    TablePagingRequest tablePagingRequest = TablePagingRequest.builder()
        .page(0)
        .pageSize(10)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .pagingRequest(tablePagingRequest)
        .sortingRequest(tableSortingRequest)
        .build();
    User aUser = User.builder()
        .id(1)
        .email("dang")
        .build();
    Specification<User> specs = (root, query, builder) -> builder.equal(root.get(User_.LAST_NAME), SEARCH_TERM);
    mockReturn(specificationBuilderServiceImpl.from(tableSearchRequest), specs);
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), PageRequest.of(0, 10));
    mockReturn(userRepository.findAll(specs, PageRequest.of(0, 10)),
        new PageImpl<>(Collections.singletonList(aUser), PageRequest.of(0, 10), 1));
    mockReturn(userConverter.toDto(aUser), UserDto.builder().email("dang").build());
    Page<UserDto> actual = service.findAllByPaging(tableSearchRequest);
    assertEquals(1, actual.getContent().size());
    assertEquals("dang", actual.getContent().get(0).getEmail());
  }

  @Test
  void findById_notFound() {
    assertThrowIllegalNotFound(() -> service.findById(null));
  }

  @Test
  void findById_notFound2() {
    int userId = 1;
    mockReturn(userRepository.findById(userId), Optional.empty());
    assertThrowIllegalNotFound(() -> service.findById(userId));
  }

  @Test
  void findById_success() {
    int userId = 1;
    User user = User.builder().build();
    mockReturn(userRepository.findById(userId), Optional.of(user));
    mockReturn(userConverter.toDto(user), UserDto.builder().build());
    service.findById(userId);
    verifyInvoke1Time(userConverter).toDto(user);
  }

  @Test
  void editUser_notFound() {
    int userId = 1;
    UserDto userDto = UserDto.builder().id(userId).build();
    mockReturn(userRepository.findById(userDto.getId()), Optional.empty());
    assertThrowIllegalNotFound(() -> service.editUser(userDto));
  }

  @Test
  void editUser_duplicatedUsername() {
    int userId = 1;
    String username = "username";
    UserDto userDto = UserDto.builder().id(userId).username("username2").build();
    User user = User.builder().id(userId).username(username).build();
    User user1 = User.builder().id(2).username("username2").build();
    mockReturn(userRepository.findById(userDto.getId()), Optional.of(user));
    mockReturn(userRepository.findByUsername(userDto.getUsername()), Optional.of(user1));
    assertThrowIllegalDuplicated(() -> service.editUser(userDto));
  }

  @Test
  void editUser_success() {
    int userId = 1;
    String username = "username";
    UserDto userDto = UserDto.builder().id(userId).username(username).build();
    User user = User.builder().id(userId).username(username).build();
    mockReturn(userRepository.findById(userDto.getId()), Optional.of(user));
    mockReturn(userRepository.findByUsername(userDto.getUsername()), Optional.of(user));
    service.editUser(userDto);
    verifyInvoke1Time(userRepository).save(user);
  }

  @Test
  void createUser_duplicateUsername() {
    int userId = 1;
    String username = "username";
    UserDto userDto = UserDto.builder().id(userId).username(username).build();
    mockReturn(userRepository.existsByUsername(userDto.getUsername()), true);
    assertThrowIllegalDuplicated(() -> service.createUser(userDto));
  }

  @Test
  void createUser_success() {
    int userId = 1;
    String username = "username";
    UserDto userDto = UserDto.builder().id(userId).username(username).build();
    User user = User.builder().id(userId).username(username).build();
    mockReturn(userRepository.existsByUsername(userDto.getUsername()), false);
    mockReturn(userConverter.toEntity(userDto), user);
    service.createUser(userDto);
    verifyInvoke1Time(userConverter).toEntity(userDto);
    verifyInvoke1Time(passwordEncoder).encode(userDto.getPassword());
    verifyInvoke1Time(userRepository).save(user);
  }

  @Test
  void updateUserRole() {
    int roleId = 1;
    String username = "username";
    RoleDto roleDto = RoleDto.builder().id(roleId).build();
    List<String> usernames = Collections.singletonList(username);
    User user = User.builder().username(username).build();
    mockReturn(userRepository.findByUsername(username), Optional.of(user));
    service.updateUserRole(roleDto, usernames);
    verifyInvoke1Time(entityManager).flush();
    verifyInvoke1Time(userRepository).findByUsername(username);
    verifyInvoke1Time(userRoleRepository).removeByRoleId(roleId);
    verifyInvoke1Time(userRoleRepository).saveAll(any());
  }

  @Test
  void updateRoleUser() {
    int userId = 2;
    String code = "role";
    UserDto userDto = UserDto.builder().id(userId).build();
    List<String> roles = Collections.singletonList(code);
    Role role = Role.builder().build();
    mockReturn(roleRepository.findByCode(code), Optional.of(role));
    service.updateRoleUser(userDto, roles);
    verifyInvoke1Time(entityManager).flush();
    verifyInvoke1Time(roleRepository).findByCode(code);
    verifyInvoke1Time(userRoleRepository).removeByUserId(userId);
    verifyInvoke1Time(userRoleRepository).saveAll(any());
  }

  @Test
  void createRoleUser() {
    User user = User.builder().build();
    String role = "role";
    List<String> roles = Collections.singletonList(role);
    mockReturn(roleRepository.findByCode(role), Optional.of(Role.builder().code(role).build()));
    service.createRoleUser(user, roles);
    verifyInvoke1Time(roleRepository).findByCode(role);
    verifyInvoke1Time(userRoleRepository).saveAll(any());
  }

  @Test
  void createUserRole() {
    Role role = Role.builder().build();
    String user = "dang";
    List<String> users = Collections.singletonList(user);
    mockReturn(userRepository.findByUsername(user), Optional.of(User.builder().username(user).build()));
    service.createUserRole(role, users);
    verifyInvoke1Time(userRepository).findByUsername(user);
    verifyInvoke1Time(userRoleRepository).saveAll(any());
  }

  @Test
  void removeUser() {
    int userId = 1;
    service.removeUser(userId);
    verifyInvoke1Time(userRoleRepository).removeByUserId(userId);
    verifyInvoke1Time(entityManager).flush();
    verifyInvoke1Time(userRepository).deleteById(userId);
  }

  @Test
  void findUserRoleListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    service.findUserRoleListView(tableSearchRequest);
    verifyInvoke1Time(userRoleService).findAll(tableSearchRequest);
  }
}
