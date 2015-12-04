package ca.uptoeleven.status.core;

public class IncidentState {

	public static class Unplanned {

		public static String INVESTIGATING = "investigating";

		public static String IDENTIFIED = "identified";

		public static String MONITORING = "monitoring";

		public static String RESOLVED = "resolved";

	}

	public static class Planned {

		public static String PENDING = "pending";

		public static String STARTED = "started";

		public static String COMPLETED = "completed";

	}

}
