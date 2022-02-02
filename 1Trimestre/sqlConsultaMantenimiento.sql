
select i.id_incidencia, p.nombre_completo, i.descripcion, i.desc_tecnica,
 i.horas, est.estado, i.fecha,i.fecha_ini_rep, i.fecha_fin_rep, urg.urgencia, 
 ubi.ubicacion, edi.edificio, i.observaciones, i.id_profesor_crea
from  man_incidencias i
inner join man_reparacion r on r.id_incidencia = i.id_incidencia
inner join fp_profesor p on p.id_profesor = r.id_profesor
inner join man_estado est on est.id_estado = i.id_estado
inner join man_urgencia urg on urg.id_urgencia = i.nivel_urgencia
inner join man_ubicacion ubi on ubi.id_ubicacion = i.id_ubicacion
inner join man_edificio edi on edi.id_edificio = ubi.id_edificio ;


select i.id_incidencia,  i.observaciones
from  man_incidencias i
inner join man_reparacion r on r.id_incidencia = i.id_incidencia
inner join fp_profesor p on p.id_profesor = r.id_profesor
inner join man_estado est on est.id_estado = i.id_estado
inner join man_urgencia urg on urg.id_urgencia = i.nivel_urgencia
inner join man_ubicacion ubi on ubi.id_ubicacion = i.id_ubicacion
inner join man_edificio edi on edi.id_edificio = ubi.id_edificio 