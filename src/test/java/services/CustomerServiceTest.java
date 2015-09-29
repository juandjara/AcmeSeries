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
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations={		
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})	
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceTest extends AbstractTest {

	// Service under test ----------------------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	// Supporting services ---------------------------------------------
	
	// Test cases ------------------------------------------------------
	
	/** REQ01. Positive test case.	
	 * A user who is not authenticated
	 * must be able to register to the system as a customer */
	@Test
	public void testRegister(){
		long oldCount = customerService.count();
		Customer customer = templateCustomer();	
		customerService.save(customer);	
		long newCount = customerService.count();	
		Assert.isTrue(newCount == oldCount + 1);
	}
	
	/** REQ01. Negative test case. 	
	 * We will try to register as a customer using an invalid phone number
	 * and we will catch the ConstraintViolationException */
	@Test(expected=ConstraintViolationException.class)	
	public void testRegisterNegative(){
		Customer customer = templateCustomer();
		// duplicate user account
		customer.setPhone("only numbers allowed");
		customerService.save(customer);	
		customerService.flush();
	}

	/** REQ32. Positive test case 
	 * A user who is authenticated as an administrator must be able to
	 * Ban/unban a customer as long as he or she is not banned/unbanned.
	 * A banned customer cannot post any comment, 
	 * rate or mark/unmark as viewed any comment. */
	@Test
	public void testBan(){
		authenticate("admin");
		customerBan();
	}
	
	/** REQ32. Negative test case 
	 * We will try to ban a customer using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testBanNegative(){
		authenticate("producer");
		customerBan();
	}
	
	// Ancillary methods -----------------------------------------------
	private Customer templateCustomer(){
		Customer result;
	
		result = customerService.create();
		result.setEmail("jd@team15.es");
		result.setName("Juan");
		result.setSurname("O.");
		result.setPhone("693203007");
		result.getUserAccount().setUsername("fuken");
		result.getUserAccount().setPassword("fuken");
		
		return result;	
	}
	
	public void customerBan(){
		// ban a customer
		int customerId = 8;
		Customer customer = customerService.findOne(customerId);
		boolean prevBanState = customer.getIsBanned();
		customerService.toggleBan(customerId);
		
		// check it was successfully banned
		customer = customerService.findOne(customerId);
		boolean newBanState = customer.getIsBanned();
		
		Assert.isTrue(prevBanState != newBanState);
	}
}