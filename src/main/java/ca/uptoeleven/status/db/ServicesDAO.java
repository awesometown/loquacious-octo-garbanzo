package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentMapper;
import ca.uptoeleven.status.db.models.Service;
import ca.uptoeleven.status.db.models.ServiceMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(ServiceMapper.class)
public interface ServicesDAO {

    @SqlUpdate("insert into services (id, name, description, serviceStatusId, createdAt, updatedAt) values (:id, :name, :description, :serviceStatusId, :createdAt, :updatedAt)")
    void insert(@BindBean Service service);

    @SqlQuery("select id, name, description, serviceStatusId, createdAt, updatedAt from services where id = :id")
    Service findById(@Bind("id" ) String id);

    @SqlQuery("select id, name, description, serviceStatusId, createdAt, updatedAt from services")
    List<Service> findAll();
}