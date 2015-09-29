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
import domain.Staff;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class StaffServiceTesst extends AbstractTest {

	public static final int NUMBER_OF_GOT_STAFF_MEMBERS = 3;
	
	// Service under test -------------------------------------------
	@Autowired
	private StaffService staffService;
	
	// Supporting services ------------------------------------------
	
	// Test cases ---------------------------------------------------

	/** REQ28. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * Create, edit and delete information about
	 * the staff members in his or her multimedia. */
	@Test
	public void testCRUD(){
		authenticate("producer");
		StaffCRUD();
	}
	
	/** REQ28. Negative test case.
	 * We will try to do the staff CRUD with a wrong authentication
	 * and we will catch the exception. */
	@Test(expected=IllegalArgumentException.class)
	public void testCRUDNegative(){
		authenticate("customer");
		StaffCRUD();
	}
	
	/** REQ29. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * List the staff this company has registered */
	@Test
	public void testList(){
		authenticate("producerHBO");
		Collection<Staff> members;
		members = staffService.findByProducerPrincipal();
		Assert.isTrue(members.size() == NUMBER_OF_GOT_STAFF_MEMBERS);
	}
	
	/** REQ29. Negative test case.
	 * We will try to do the staff listing with a wrong authentication
	 * and we will catch the exception. */
	@Test(expected=IllegalArgumentException.class)
	public void testListNegative(){
		authenticate("customer");
		Collection<Staff> members;
		members = staffService.findByProducerPrincipal();
		Assert.isTrue(members.size() == NUMBER_OF_GOT_STAFF_MEMBERS);
	}
	
	// Ancillary methods --------------------------------------------
	public Staff templateStaff(){
		Staff result;
		result = staffService.create();
		result.setEmail("test@test.com");
		result.setName("Mr");
		result.setSurname("Tested");
		
		return result;
	}
	
	public void StaffCRUD(){
		// Creating a new staff member
		int oldSize = staffService.findByProducerPrincipal().size();
		Staff member = templateStaff();
		member = staffService.save(member);
		
		// Checking the number of staff members has increased
		int newSize = staffService.findByProducerPrincipal().size();
		Assert.isTrue(newSize == oldSize + 1);
		
		// Deleting the previously created staff member
		staffService.delete(member);
		
		// Checking the number of staff members has decreased
		Collection<Staff> finalCollection = staffService.findByProducerPrincipal();
		int newSize2 = finalCollection.size();
		Assert.isTrue(newSize2 == newSize - 1);
	}
}
