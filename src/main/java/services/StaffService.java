package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StaffRepository;
import domain.ProducerCompany;
import domain.Role;
import domain.Staff;

@Transactional
@Service
public class StaffService {

	// Managed repository ----------------------------------------------------
	@Autowired
	private StaffRepository staffRepository;
	
	// Supporting services ---------------------------------------------------
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private RoleService roleService;
	
	// Simple CRUD methods ---------------------------------------------------
	public Staff findOne(int staffId){
		Staff result = staffRepository.findOne(staffId);
		return result;
	}
	
	public Staff create(){
		Staff result;
		ProducerCompany producer;
		Collection<Role> roles;
		
		result = new Staff();
		producer = producerService.findByPrincipal();
		roles = new ArrayList<Role>();
		
		result.setProducer(producer);
		result.setRoles(roles);
		
		return result;
	}
	
	public Staff save(Staff staff){
		Staff result;
		
		Assert.notNull(staff);
		checkPrincipal(staff);
		result = staffRepository.save(staff);
		
		return result;
	}
	
	public void delete(Staff staff){
		Assert.notNull(staff);
		checkPrincipal(staff);
		// delete all roles of this staff
		roleService.delete(staff.getRoles());
		// delete staff
		staffRepository.delete(staff);
	}
	
	// Other business methods ------------------------------------------------
	public void checkPrincipal(Staff staff){
		ProducerCompany principal = producerService.findByPrincipal();
		ProducerCompany producer = staff.getProducer();
		Assert.isTrue(principal.equals(producer));
	}
	
	public Collection<Staff> findByProducerPrincipal(){
		Collection<Staff> result;
		ProducerCompany principal;
		
		principal = producerService.findByPrincipal();
		result = staffRepository.findByProducer(principal);
		
		return result;
	}
}
