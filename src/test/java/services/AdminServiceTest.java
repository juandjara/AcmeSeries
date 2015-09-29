package services;
	
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utils.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations={		
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})	
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdminServiceTest extends AbstractTest {

	// Service under test ----------------------------------------------
	
	@Autowired
	private AdministratorService adminService;
	
	// Supporting services ---------------------------------------------
	
	// Test cases ------------------------------------------------------
	
	/** REQ31. Positive test case.	
	 * A user who is authenticated as an administrator must be able to
	 * register another administrator to the system */
	@Test
	public void testRegister(){
		authenticate("admin");
		long oldCount = adminService.count();
		Administrator admin = templateAdmin();	
		adminService.save(admin);	
		long newCount = adminService.count();	
		Assert.isTrue(newCount == oldCount + 1);
	}
	
	/** REQ31. Negative Test case. 	
	 * We will try to register an administrator using an invalid phone number
	 * and we will catch the ConstraintViolationException */
	@Test(expected=ConstraintViolationException.class)	
	public void testRegisterNegative(){
		authenticate("admin");
		Administrator admin = templateAdmin();
		admin.setPhone("no text allowed");
		adminService.save(admin);
		adminService.flush();
	}

	// Ancillary methods -----------------------------------------------
	private Administrator templateAdmin(){
		Administrator result;
	
		result = adminService.create();
		result.setEmail("jd@team15.es");
		result.setName("Juan");
		result.setSurname("O.");
		result.setPhone("693203007");
		result.getUserAccount().setUsername("fuken");
		result.getUserAccount().setPassword("fuken");
		
		return result;	
	}		
}