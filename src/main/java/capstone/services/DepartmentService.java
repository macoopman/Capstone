package capstone.services;

import capstone.domain.Department;
import capstone.domain.Klass;
import capstone.dto.NewClassDto;
import capstone.exceptions.DepartmentServiceException;
import capstone.repositories.DepartmentRepository;
import capstone.repositories.KlassRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

   private KlassRepository klassRepository;
   private DepartmentRepository departmentRepository;

   public DepartmentService(KlassRepository klassRepository, DepartmentRepository departmentRepository) {
      this.klassRepository = klassRepository;
      this.departmentRepository = departmentRepository;
   }

   public void addClass(long id, NewClassDto dto) {

      Department department = departmentRepository.findById(id).orElseThrow( () -> new DepartmentServiceException("Department Not Found"));

      Klass klass = new Klass();
      klass.setSubject(dto.getSubject());
      klass.setClassNumber(dto.getClassNumber());
      klass.setDescription(dto.getDescription());
      klass.setDepartment(department);

      klassRepository.save(klass);

   }
}
