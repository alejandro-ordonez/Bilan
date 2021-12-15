package org.bilan.co.application.dashboards;

import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IMunicipality;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;

import java.util.List;

interface DashboardFunction {
    static DataModel.StudentDashboard build(List<IPerformanceActivity> activities, List<IPerformanceGame> games, Students student) {
        return new DataModel.StudentDashboard(activities, games, student);
    }

    static DataModel.CourseGradeDashboard build(List<IPerformanceActivity> activities, List<IPerformanceGame> games, List<Students> students, List<Object[]> logins) {
        return new DataModel.CourseGradeDashboard(activities, games, students, logins);
    }

    static DataModel.MunicipalityDashboard buildMun(List<IPerformanceActivity> activities, List<IPerformanceGame> games, List<ICollege> colleges, List<Object[]> logins) {
        return new DataModel.MunicipalityDashboard(activities, games, colleges, logins);
    }

    static DataModel.CollegeDashboard build(List<IPerformanceActivity> activities, List<IPerformanceGame> games, ICollege college, List<Object[]> logins) {
        return new DataModel.CollegeDashboard(activities, games, college, logins);
    }

    static DataModel.StateDashboard buildState(List<IPerformanceActivity> activities, List<IPerformanceGame> games, List<IMunicipality> muns, List<Object[]> logins) {
        return new DataModel.StateDashboard(activities, games, muns, logins);
    }

    static DataModel.MainDashboard buildMainDashboard(List<IPerformanceActivity> activities, List<IPerformanceGame> games, List<String> states, List<Object[]> logins) {
        return new DataModel.MainDashboard(activities, games, states, logins);
    }
}
