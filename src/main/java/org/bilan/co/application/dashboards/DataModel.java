package org.bilan.co.application.dashboards;

import lombok.Data;
import lombok.Getter;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IMunicipality;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;

import java.util.List;

public class DataModel {
    @Data
    private static class DefaultDataDashboard {
        private final List<IPerformanceActivity> performanceActivities;
        private final List<IPerformanceGame> performanceGames;
    }

    @Getter
    public static class MainDashboard extends DefaultDataDashboard {
        private final List<String> states;

        public MainDashboard(List<IPerformanceActivity> performanceActivities,
                             List<IPerformanceGame> performanceGames, List<String> states) {
            super(performanceActivities, performanceGames);
            this.states = states;
        }
    }

    @Getter
    public static class StateDashboard extends DefaultDataDashboard {
        private final List<IMunicipality> municipalities;

        public StateDashboard(List<IPerformanceActivity> performanceActivities,
                              List<IPerformanceGame> performanceGames, List<IMunicipality> municipalities) {
            super(performanceActivities, performanceGames);
            this.municipalities = municipalities;
        }
    }

    @Getter
    public static class MunicipalityDashboard extends DefaultDataDashboard {
        private final List<ICollege> colleges;

        public MunicipalityDashboard(List<IPerformanceActivity> performanceActivities,
                                     List<IPerformanceGame> performanceGames, List<ICollege> colleges) {
            super(performanceActivities, performanceGames);
            this.colleges = colleges;
        }
    }

    @Getter
    public static class CollegeDashboard extends DefaultDataDashboard {
        private final ICollege college;

        public CollegeDashboard(List<IPerformanceActivity> performanceActivities,
                                List<IPerformanceGame> performanceGames, ICollege college) {
            super(performanceActivities, performanceGames);
            this.college = college;
        }
    }

    @Getter
    public static class CourseGradeDashboard extends DefaultDataDashboard {
        private final List<Students> students;

        public CourseGradeDashboard(List<IPerformanceActivity> performanceActivities,
                                    List<IPerformanceGame> performanceGames, List<Students> students) {
            super(performanceActivities, performanceGames);
            this.students = students;
        }
    }

    @Getter
    public static class StudentDashboard extends DefaultDataDashboard {
        private final Students student;

        public StudentDashboard(List<IPerformanceActivity> performanceActivities,
                                List<IPerformanceGame> performanceGames, Students student) {
            super(performanceActivities, performanceGames);
            this.student = student;
        }
    }
}
