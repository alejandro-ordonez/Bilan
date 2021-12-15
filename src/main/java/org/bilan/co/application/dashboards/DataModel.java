package org.bilan.co.application.dashboards;

import lombok.Data;
import lombok.Getter;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IMunicipality;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;

import java.util.List;

class DataModel {
    @Data
    private static class DefaultDataDashboard {
        private final List<IPerformanceActivity> performanceActivities;
        private final List<IPerformanceGame> performanceGames;
    }

    @Getter
    public static class MainDashboard extends DefaultDataDashboard {
        private final List<String> states;
        private final List<Object[]> logins;

        public MainDashboard(List<IPerformanceActivity> performanceActivities,
                             List<IPerformanceGame> performanceGames,
                             List<String> states,
                             List<Object[]> logins) {
            super(performanceActivities, performanceGames);
            this.states = states;
            this.logins = logins;
        }
    }

    @Getter
    public static class StateDashboard extends DefaultDataDashboard {
        private final List<IMunicipality> municipalities;
        private final List<Object[]> logins;

        public StateDashboard(List<IPerformanceActivity> performanceActivities,
                              List<IPerformanceGame> performanceGames,
                              List<IMunicipality> municipalities,
                              List<Object[]> logins) {
            super(performanceActivities, performanceGames);
            this.municipalities = municipalities;
            this.logins = logins;
        }
    }

    @Getter
    public static class MunicipalityDashboard extends DefaultDataDashboard {
        private final List<ICollege> colleges;
        private final List<Object[]> logins;

        public MunicipalityDashboard(List<IPerformanceActivity> performanceActivities,
                                     List<IPerformanceGame> performanceGames,
                                     List<ICollege> colleges,
                                     List<Object[]> logins) {
            super(performanceActivities, performanceGames);
            this.colleges = colleges;
            this.logins = logins;
        }
    }

    @Getter
    public static class CollegeDashboard extends DefaultDataDashboard {
        private final ICollege college;
        private final List<Object[]> logins;

        public CollegeDashboard(List<IPerformanceActivity> performanceActivities,
                                List<IPerformanceGame> performanceGames,
                                ICollege college,
                                List<Object[]> logins) {
            super(performanceActivities, performanceGames);
            this.college = college;
            this.logins = logins;
        }
    }

    @Getter
    public static class CourseGradeDashboard extends DefaultDataDashboard {
        private final List<Students> students;
        private final List<Object[]> logins;

        public CourseGradeDashboard(List<IPerformanceActivity> performanceActivities,
                                    List<IPerformanceGame> performanceGames,
                                    List<Students> students,
                                    List<Object[]> logins) {
            super(performanceActivities, performanceGames);
            this.students = students;
            this.logins = logins;
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
