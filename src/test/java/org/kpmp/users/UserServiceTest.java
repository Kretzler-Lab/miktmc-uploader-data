package org.kpmp.users;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.packages.Package;
import org.kpmp.packages.PackageRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private PackageRepository packageRepository;
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(packageRepository);
    }

    @After
    public void tearDown() throws Exception {
        userService = null;
    }

    @Test
    public void testFindAllWithPackages() {
        Package package1 = new Package();
        User user1 = new User();
        user1.setId("1");
        package1.setSubmitter(user1);
        List<Package> packageList = new ArrayList<>(Arrays.asList(package1));
        when(packageRepository.findAll()).thenReturn(packageList);
        assertEquals(1, userService.findAllWithPackages().size());
        assertEquals("1", userService.findAllWithPackages().get(0).getId());
    }
}
