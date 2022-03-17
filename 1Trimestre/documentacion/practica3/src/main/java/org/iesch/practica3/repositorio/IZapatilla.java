package org.iesch.practica3.repositorio;

import org.iesch.practica3.model.Zapatillas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IZapatilla extends JpaRepository<Zapatillas, Long>{
    
}
