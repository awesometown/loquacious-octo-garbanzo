package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;


public class ServiceListView extends View {

	@Getter
	private List<ServiceViewModel> services;

	public ServiceListView(List<ServiceViewModel> services) {
		super("service_list.ftl");
		this.services = services;
	}

}
