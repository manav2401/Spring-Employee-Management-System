package com.springEMS.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springEMS.handler.CustomException;
import com.springEMS.team.TeamService;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private TeamService teamServ;
	
	public ArrayList<Employee> getEmployeeList(){
		ArrayList<Employee> list = new ArrayList<Employee>();
		empRepo.findAll().forEach(list::add);
		return list;
		
	}
	
	public Employee getEmployeeSpecs(String id) throws Exception {
		Employee emp = empRepo.findOne(id);
		if(emp == null) {
			throw new CustomException("Entity Not Found");
		}
		return emp;
	}
	
	public void addEmployee(Employee emp) throws CustomException{
		if(this.validateEmployee(emp)) {
			empRepo.save(emp);
		}	
		return;
		
	}

	public void updateEmployee(String id, Employee emp) {
		empRepo.save(emp);
		return ;
		
	}
	
	public void deleteEmployee(String id) throws CustomException {
		Employee emp = empRepo.findOne(id);
		if(emp ==null) {
			throw new CustomException("Entity Not Found");
		}
		else {
			teamServ.deleteTeamMemberUsingEmployeeId(emp);
			empRepo.delete(id);
		}	
	}
	
	public boolean checkIfEmployeeExists(String id) {
		return empRepo.exists(id);
	}

	public boolean validateEmployee(Employee emp) throws CustomException {
		if(emp.getEmp_id() == null || emp.getEmp_id().equals("") || emp.getEmp_id().length()>20) {
			throw new CustomException("Invalid Field Employee ID");
		}
		
		if(emp.getF_name() == null || emp.getF_name().equals("") || emp.getF_name().length()>20) {
			throw new CustomException("Invalid Field First Name");
		}
		if(emp.getL_name() == null || emp.getL_name().equals("") || emp.getL_name().length()>20) {
			throw new CustomException("Invalid Field Last Name");
		}
		if(emp.getDob() == null || emp.getDob().equals("") || emp.getDob().length()>10) {
			throw new CustomException("Invalid Field DOB");
		}
		if(emp.getJob_title() == null || emp.getJob_title().equals("") || emp.getJob_title().length()>20) {
			throw new CustomException("Invalid Field Job Title");
		}
		if(emp.getPhone_no() == null || emp.getPhone_no().equals("") || emp.getPhone_no().length()>13) {
			throw new CustomException("Invalid Field Phone Number");
		}
		return true;
	}
	
}