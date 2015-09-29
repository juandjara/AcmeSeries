package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoleRepository;
import domain.Multimedia;
import domain.ProducerCompany;
import domain.Role;
import domain.Staff;
import forms.RoleForm;

@Transactional
@Service
public class RoleService {

	// Managed repository ----------------------------------------------------
	@Autowired
	private RoleRepository roleRepository;
	
	// Supporting services ---------------------------------------------------
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private MultimediaService mediaService;
	
	// Simple CRUD methods ---------------------------------------------------
	public Role findOne(int roleId){
		Role result = roleRepository.findOne(roleId);
		return result;
	}
	
	public Role create(Multimedia media, Staff staff){
		Role result;
		 	
		result = new Role();
		result.setMultimedia(media);
		result.setStaff(staff);
		
		return result;
	}
	
	public Role save(Role role){
		Role result;
		
		Assert.notNull(role);
		checkPrincipal(role);
		result = roleRepository.save(role);
		
		return result;
	}
	
	public void flush(){roleRepository.flush();};
	
	public void delete(Role role){
		Assert.notNull(role);
		checkPrincipal(role);
		roleRepository.delete(role);
	}
	
	public void delete(Collection<Role> roles){
		Assert.notNull(roles);
		for(Role r:roles){
			checkPrincipal(r);
		}
		roleRepository.delete(roles);
	}
	
	// Other business methods ------------------------------------------------
	public void checkPrincipal(Role role){
		ProducerCompany principal = producerService.findByPrincipal();
		ProducerCompany mediaProducer = role.getMultimedia().getProducerCompany();
		ProducerCompany staffProducer = role.getStaff().getProducer();
		Assert.isTrue(principal.equals(mediaProducer));
		Assert.isTrue(principal.equals(staffProducer));
	}
	
	public Collection<Role> findByMultimedia(Multimedia media){
		Collection<Role> result;
		result = roleRepository.findByMultimedia(media);
		return result;
	}
	
	public RoleForm construct(Role role){
		Assert.notNull(role);
		RoleForm result;
		
		result = new RoleForm();
		result.setRole(role.getRole());
		result.setMediaId(role.getMultimedia().getId());
		result.setStaffId(role.getStaff().getId());
		result.setId(role.getId());
		
		return result;
	}
	
	public Role reconstruct(RoleForm form){
		Assert.notNull(form);
		
		int mediaId = form.getMediaId();
		int staffId = form.getStaffId();
		Role result;
		Multimedia media;
		Staff staff;
		
		// check principal for multimedia and staff
		media = mediaService.findOne(mediaId);
		staff = staffService.findOne(staffId);
		mediaService.checkPrincipal(media);
		staffService.checkPrincipal(staff);
		
		// if a new role is being created, use create()
		// and set role property
		if(form.getId() == 0){
			result = create(media, staff);
			result.setRole(form.getRole());
		// else if an old role is being edited, retrieve it with findOne(),
		// change its role property and return it.
		// business rule: staff and media ids cannot be edited
		}else{
			int id = form.getId();
			Role previousRole = findOne(id);
			String roletxt = form.getRole();
			previousRole.setRole(roletxt);
			result = previousRole;
		}
		
		return result;
	}
}
