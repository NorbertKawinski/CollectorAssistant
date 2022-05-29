package net.kawinski.collecting.startup.helpers.model;

import lombok.ToString;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.user.UserRegistrationForm;
import net.kawinski.collecting.startup.helpers.base.StartupObject;
import net.kawinski.utils.java.JavaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents and creates the sample (initial) structure of the entities.
 *
 * Classes are structured in a way to resemble a tree.
 * Thanks to this we can:
 * * Avoid name conflicts while using shorter names and reuse same names in other independent parts of the trees
 * * Reduce complexity of having tens of variables in a single scope (which reduces probability of accidentally picking wrong variable)
 */
@ToString
public class StartupCA extends StartupObject {
    public final User usrAdmin;
    public final User usrCataloger;
    public final User usr1;
    public final User usr2;
    public final User usr3;
    public final List<User> testUsers = new ArrayList<>();
    public final CatRoot catRoot;

    public StartupCA() {
        // I know it's bad. But it's one-time initialization only
        //noinspection AssignmentToStaticFieldFromInstanceMethod,AssignmentToSuperclassField,ThisEscapedInObjectConstruction
        ca = this;

        usrAdmin = newUser(Resources.DEFAULT_ADMIN_USERNAME, Resources.DEFAULT_ADMIN_MAIL, Resources.DEFAULT_ADMIN_PASSWORD);
        usrAdmin.getImage().setImage(newUpload(usrAdmin, "users/A.png"));
        srv.getUserService().addGroups(usrAdmin, Group.ADMIN);
        usrCataloger = newUser(Resources.DEFAULT_USER_CATALOGER_USERNAME, Resources.DEFAULT_USER_CATALOGER_MAIL, Resources.DEFAULT_USER_CATALOGER_PASSWORD);
        srv.getSearchUploadUser().registerIfNeeded();

        usr1 = newUser(Resources.DEFAULT_USER1_USERNAME, Resources.DEFAULT_USER1_MAIL, Resources.DEFAULT_USER1_PASSWORD);
        usr1.getImage().setImage(newUpload(usr1, "users/one.png"));
        usr2 = newUser(Resources.DEFAULT_USER2_USERNAME, Resources.DEFAULT_USER2_MAIL, Resources.DEFAULT_USER2_PASSWORD);
        usr2.getImage().setImage(newUpload(usr2, "users/two.png"));
        usr3 = newUser(Resources.DEFAULT_USER3_USERNAME, Resources.DEFAULT_USER3_MAIL, Resources.DEFAULT_USER3_PASSWORD);
        usr3.getImage().setImage(newUpload(usr3, "users/three.png"));

        String testUsersFile = new String(JavaUtils.readCaResource("uploads/users/test_users/test_users.txt"));
        int avatarNum = 1;
        for (String testUserLine : testUsersFile.split("\n")) {
            String[] testUserNameArr = testUserLine.trim().split("\t");
            String testUserName = testUserNameArr[0];
            String testUserSurname = testUserNameArr[1];
            String testUserMail = testUserName + "." + testUserSurname + "@ca.kawinski.net";
            User testUser = newUser(testUserName + "_" + testUserSurname, testUserMail, Resources.DEFAULT_TESTUSER_PASSWORD);
            testUser.getImage().setImage(newUpload(testUser, "users/test_users/avatar (" + avatarNum++ + ").jpg"));
            testUsers.add(testUser);
        }

        catRoot = new CatRoot();
    }

    public final User newUser(final String name, final String mail, final String password) {
        return srv.getUserService().register(new UserRegistrationForm(name, mail, password, password, true));
    }
}
