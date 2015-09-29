package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.ProducerCompany;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ProducerCompanyServiceTest {

	// Service under test ------------------------------------------------
	@Autowired
	private ProducerCompanyService producerService;
	
	// Supporting services -----------------------------------------------
	
	// Test cases --------------------------------------------------------
	/** REQ02. Positive test case.
	 * A user who is not authenticated
	 * must be able to register to the system as a producer */
	@Test
	public void testRegister(){
		long oldCount = producerService.count();
		ProducerCompany producer = templateProducer();
		producerService.save(producer);
		long newCount = producerService.count();
		Assert.isTrue(newCount == oldCount + 1);
	}
	/** REQ02. Negative Test case. 
	 * We will try to register as a producer using an invalid phone number
	 * and we will catch the ConstraintViolationException */
	@Test(expected=ConstraintViolationException.class)
	public void testRegisterNegative(){
		ProducerCompany producer = templateProducer();
		producer.setPhone("no text allowed");
		producerService.save(producer);
	}
	
	// Ancillary methods -------------------------------------------------
	private ProducerCompany templateProducer(){
		ProducerCompany result;
		Date originOfTime;
		
		originOfTime = new Date(0);
		result = producerService.create();
		result.setEmail("test@producers.com");
		result.setFundationDate(originOfTime);
		result.setName("Generical");
		result.setSurname("Inc. ");
		result.setPhone("000000000");
		result.setWebpage("http://generical.biz");
		result.getUserAccount().setUsername("G-producer");
		result.getUserAccount().setPassword("G-producer");
		
		return result;
	}
	
}
