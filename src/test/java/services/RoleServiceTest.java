package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utils.AbstractTest;
import domain.Multimedia;
import domain.Role;
import domain.Staff;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RoleServiceTest extends AbstractTest{

	public static final int NUMBER_OF_GOT_ROLES = 3;
	
	// Service under test ----------------------------------------------
	@Autowired
	private RoleService roleService;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private MultimediaService mediaService;
	@Autowired
	private StaffService staffService;
	
	// Test cases ------------------------------------------------------
	
	/** REQ09. Positive test case. 
	 * Every user, authenticated or not, must be able to
	 * List the roles in a multimedia and the name and surname 
	 * of the staff of the staff member who plays that role */
	@Test
	public void testList(){
		// list the roles in a multimedia
		int validId = 24;
		Multimedia media = mediaService.findOne(validId);
		Assert.notNull(media);
		Collection<Role> roles = media.getRoles();
		Assert.isTrue(roles.size() == NUMBER_OF_GOT_ROLES);
	}
	
	/** REQ09. Negative test case. 
	 * We will try to do the role listing using a invalid id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void testListNegative(){
		int invalidId = 244;
		Multimedia media = mediaService.findOne(invalidId);
		Assert.notNull(media);
		Collection<Role> roles = media.getRoles();
		Assert.isTrue(roles.size() == NUMBER_OF_GOT_ROLES);
	}
	
	
	/** REQ27. Positive test case. 
	 * A user authenticated as a producer company must be able to
	 * Manage the roles the staff has
	 * in the multimedia the company has created.
	 * Managing implies creating, modifying and deleting them. */
	@Test
	public void testCRUD(){
		authenticate("producerHBO");
		roleCRUD();
	}
	
	/** REQ27. Negative test case. 
	 * We will try to do the CRUD of a Role using an invalid authentication 
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCRUDNegative(){
		authenticate("customer");
		roleCRUD();
	}
	
	// Ancillary methods ------------------------------------------------
	public Role templateRole(){
		Role result;
		
		Multimedia media = mediaService.findOne(24);
		Staff staff = staffService.findOne(19);
		result = roleService.create(media, staff);
		result.setRole("COMPOSER");
		
		return result;
	}
	
	public void roleCRUD(){
		// Creating a new role
		Multimedia media = mediaService.findOne(24);
		int oldSize = media.getRoles().size();
		Role role = templateRole();
		role = roleService.save(role);
		
		media.getRoles().add(role);
		mediaService.save(media);
		
		// Checking the number of roles has increased
		media = mediaService.findOne(24);
		int newSize = media.getRoles().size();
		Assert.isTrue(newSize == oldSize + 1);
		
		// Deleting the previously created role
		roleService.delete(role);
		media.getRoles().remove(role);
		mediaService.save(media);
			
		// Checking that the number of roles has decreased
		media = mediaService.findOne(24);
		int newSize2 = media.getRoles().size();
		Assert.isTrue(newSize2 == newSize -1);
	}
	
}
