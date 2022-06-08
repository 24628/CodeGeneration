package io.swagger.services.services;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.exceptions.EntityNotFoundException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.helpers.OffsetPageableUUID;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Request.UserRequest;
import io.swagger.repository.IAccountDTO;
import io.swagger.repository.ITransactionDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.UserService;
import io.swagger.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, UserService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceTest {
    @Mock
    private IUserDTO userDTO;
    @Mock
    private Validator validator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IAccountDTO iAccountDTO;
    @Mock
    ITransactionDTO transactionDTO;
    @InjectMocks
    private UserService userService;
    private UserEntity userEntity;
    private UserEntity userEntity2;
    private UserRequest userRequest;
    @Before
    public void setupMock() {
        userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("johndoe@example.com")
                .name("john")
                .username("johndoe")
                .password(passwordEncoder.encode("password"))
                .pinCode(1234)
                .role(Roles.EMPLOYEE)
                .transactionLimit(2000L)
                .build();

        userEntity2 = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .dayLimit(2000L)
                .email("johndoe2@example.com")
                .name("john2")
                .username("johndoe2")
                .password(passwordEncoder.encode("password"))
                .pinCode(1234)
                .role(Roles.CUSTOMER)
                .transactionLimit(2000L)
                .build();

        userRequest = new UserRequest();
        userRequest.setUsername(userEntity.getUsername());
        userRequest.setEmail(userEntity.getEmail());
        userRequest.setPassword("password");
        userRequest.setName(userEntity.getName());
        userRequest.setTransactionLimit(userEntity.getTransactionLimit());
        userRequest.setDayLimit(userEntity.getDayLimit());
        userRequest.setRole(String.valueOf(userEntity.getRole()));
    }

    @Test
    public void CanSaveAnUserObject() {
        given(userDTO.save(userEntity))
                .willReturn(userEntity);

        given(userDTO.findByUsername(userEntity.getUsername()))
                .willReturn(null);

        UserEntity savedUser = userService.addUser(userRequest);

        System.out.println(savedUser);

        assertNotNull(savedUser);
    }

    @Test
    public void findUserByName() {
        given(userDTO.findByUsername(userEntity.getUsername()))
                .willReturn(userEntity);

        UserEntity savedUser = userService.findUserByName(userRequest.getUsername());

        System.out.println(savedUser);

        assertNotNull(savedUser);
    }

    @Test
    public void getUsers(){
        Integer limit = 10;
        Integer offset = 0;
        given(userDTO.findAllByRoleIsNot(Roles.BANK,new OffsetPageableUUID(limit,offset)))
                .willReturn(List.of(userEntity,userEntity2));

        List<UserEntity> UserEntityList = userService.getUsers(limit, offset);

        // then - verify the output
        assertNotNull(UserEntityList);
    }

    @Test
    public void getUserById(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(userEntity);

        UserEntity found = userService.getUserById(String.valueOf(userEntity.getUuid()));

        assertNotNull(found);
    }

    @Test
    public void getUserByIdNotFound(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(String.valueOf(userEntity.getUuid()));
        });
    }

    @Test
    public void updateUser(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(userEntity);

        userRequest.setTransactionLimit(3000L);
        UserEntity updatedUserEntity = userService.updateUser(String.valueOf(userEntity.getUuid()), userRequest);

        assertEquals(Optional.of(3000L), Optional.of(updatedUserEntity.getTransactionLimit()));
    }

    @Test
    public void updateUserWillThrowEntityNotFound(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(null);

        userRequest.setTransactionLimit(3000L);
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(String.valueOf(userEntity.getUuid()), userRequest);
        });
    }

    @Test
    public void updateUserWillThrowValidationException(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(userEntity);

        userRequest.setTransactionLimit(-100L);
        org.junit.jupiter.api.Assertions.assertThrows(ValidationException.class, () -> {
            userService.updateUser(String.valueOf(userEntity.getUuid()), userRequest);
        });
    }

    @Test
    public void deleteUser(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(userEntity);

        UserEntity updatedUserEntity = userService.deleteUser(String.valueOf(userEntity.getUuid()));

        assertEquals(Optional.of(Roles.DISABLED), Optional.of(updatedUserEntity.getRole()));
    }

    @Test
    public void deleteUserWillThrowEntityNotFound(){
        given(userDTO.getOne(userEntity.getUuid()))
                .willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(String.valueOf(userEntity.getUuid()));
        });
    }

    @Test
    public void getUsersWithNoAccount(){
        //@todo dit werkt niet elep

//        Integer limit = 10;
//        Integer offset = 0;
//        given(userDTO.findAllByRoleIs(Roles.CUSTOMER))
//                .willReturn(List.of(userEntity,userEntity2));
//
//        given(iAccountDTO.findByUserIdIs(userEntity.getUuid()))
//                .willReturn(false);
//
//        List<UserEntity> UserEntityList = userService.getUsersWithNoAccount(offset, limit);
//
//        assertNotNull(UserEntityList);
    }

}
