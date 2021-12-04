package org.bilan.co.application.dashboards;

import lombok.Data;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IMunicipality;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;

import java.util.List;

public class DataModel {
    @Data
    private static class DefaultDataDashboard {
        private List<IPerformanceActivity> performanceActivities;
        private List<IPerformanceGame> performanceGames;
    }

    @Data
    public static class MainDashboard extends DefaultDataDashboard {
        private List<String> states;
    }

    @Data
    public static class StateDashboard extends DefaultDataDashboard {
        private List<IMunicipality> municipalities;
    }

    @Data
    public static class MunicipalityDashboard extends DefaultDataDashboard {
        private List<ICollege> colleges;
    }

    @Data
    public static class CollegeDashboard extends DefaultDataDashboard {
        private ICollege college;
    }

    @Data
    public static class CourseGradeDashboard extends DefaultDataDashboard {
        private List<Students> students;
    }

    @Data
    public static class StudentDashboard extends DefaultDataDashboard {
        private Students student;
    }
}
