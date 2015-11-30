package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.ServiceStatus;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(ServiceStatusMapper.class)
public interface ServiceStatusesDAO {

	@SqlQuery("select id, name, displayColor, sortOrder from serviceStatuses")
	List<ServiceStatus> findAll();

	@SqlQuery("select id, name, displayColor, sortOrder from serviceStatuses where id = :id")
	ServiceStatus findById(@Bind("id") String id);

}
