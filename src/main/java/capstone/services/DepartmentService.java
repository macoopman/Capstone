package capstone.services;

import capstone.domain.Department;
import capstone.domain.Klass;
import capstone.dto.NewClassDto;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.InvalidIdException;
import capstone.repositories.DepartmentRepository;
import capstone.repositories.KlassRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

   private KlassRepository klassRepository;
   private DepartmentRepository departmentRepository;

   public DepartmentService(KlassRepository klassRepository, DepartmentRepository departmentRepository) {
      this.klassRepository = klassRepository;
      this.departmentRepository = departmentRepository;
   }

   public void addClass(long id, NewClassDto dto) {

      Optional<Department> department = departmentRepository.findById(id);
      if (!department.isPresent()) throw new InvalidIdException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());

      Klass klass = new Klass();
      klass.setSubject(dto.getSubject());
      klass.setClassNumber(dto.getClassNumber());
      klass.setDescription(dto.getDescription());
      klass.setDepartment(department.get());

      klassRepository.save(klass);

   }
}
