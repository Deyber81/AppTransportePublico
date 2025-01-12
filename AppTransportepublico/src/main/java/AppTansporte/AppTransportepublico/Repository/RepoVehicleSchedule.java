package AppTansporte.AppTransportepublico.Repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import AppTansporte.AppTransportepublico.Entity.TRoute;
import AppTansporte.AppTransportepublico.Entity.TVehicle;
import AppTansporte.AppTransportepublico.Entity.TVehicleSchedule;
import jakarta.transaction.Transactional;
@Repository
public interface RepoVehicleSchedule extends JpaRepository<TVehicleSchedule, String> {

     @Query("SELECT r.operationalStartTime FROM TRoute r WHERE r.idRoute = :idRoute")
     LocalTime findRouteStartTimeById(@Param("idRoute") String idRoute);

    // Consulta para encontrar una ruta por su ID
    @Query("SELECT r FROM TRoute r WHERE r.idRoute = :idRoute")
    TRoute findRouteById(@Param("idRoute") String idRoute);

    // Consulta para encontrar un vehículo por su ID
    @Query("SELECT v FROM TVehicle v WHERE v.idVehicle = :idVehicle")
    TVehicle findVehicleById(@Param("idVehicle") String idVehicle);

    @Query("SELECT r.operationalEndTime FROM TRoute r WHERE r.idRoute = :idRoute")
    LocalTime findRouteEndTimeById(@Param("idRoute") String idRoute);

    @Query("SELECT s FROM TVehicleSchedule s WHERE s.idVehicle = :idVehicle AND s.dayOfWeek = :dayOfWeek " +
       "AND ((s.departureTime < :endTime AND s.departureTime >= :startTime) " +
       "OR (s.arrivalTime > :startTime AND s.arrivalTime <= :endTime))")
     List<TVehicleSchedule> findByVehicleAndDay(
        @Param("idVehicle") String idVehicle,
        @Param("dayOfWeek") String dayOfWeek,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime);

      @Query("SELECT r.routeDuration FROM TRoute r WHERE r.idRoute = :idRoute")
      Integer findRouteDurationById(@Param("idRoute") String idRoute);

      @Query("SELECT r.breakInterval FROM TRoute r WHERE r.idRoute = :idRoute")
      Integer findBreakIntervalById(@Param("idRoute") String idRoute);

      @Query("SELECT s FROM TVehicleSchedule s WHERE s.idVehicle = :idVehicle AND s.dayOfWeek = :dayOfWeek")
      List<TVehicleSchedule> findByVehicleAndDayDelete(@Param("idVehicle") String idVehicle, @Param("dayOfWeek") String dayOfWeek);

         // Buscar horarios por vehículo y día
      @Query("SELECT s FROM TVehicleSchedule s WHERE s.idVehicle = :idVehicle AND s.dayOfWeek = :dayOfWeek")
      List<TVehicleSchedule> findByVehicleAndDay(@Param("idVehicle") String idVehicle, @Param("dayOfWeek") String dayOfWeek);

      @Modifying
      @Transactional
      @Query("DELETE FROM TVehicleSchedule s WHERE s.idVehicle = :idVehicle AND s.dayOfWeek = :dayOfWeek")
      int deleteByVehicleAndDay(@Param("idVehicle") String idVehicle, @Param("dayOfWeek") String dayOfWeek);
}
