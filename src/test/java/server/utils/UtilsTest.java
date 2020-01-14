package server.utils;

import com.server.model.enums.Role;
import com.server.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void getRoleByEmailTest() {
        String emailProfessor = "ana_toma@cs.ubbcluj.ro";
        String emailStudent = "casd1924@scs.ubbcluj.ro";
        String emailNotSupported = "test1234@yahoo.com";

        Assert.assertEquals(Role.PROFESSOR, Utils.getRoleByEmail(emailProfessor));
        Assert.assertEquals(Role.STUDENT, Utils.getRoleByEmail(emailStudent));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail(emailNotSupported));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail("@cs.ubbcluj.ro"));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail("@scs.ubbcluj.ro"));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail(""));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail("@scs.ubbcluj.ro@cs.ubbcluj.ro"));
        Assert.assertEquals(Role.NOT_SUPPORTED, Utils.getRoleByEmail("@cs.ubbcluj.ro@scs.ubbcluj.ro"));
    }
}
