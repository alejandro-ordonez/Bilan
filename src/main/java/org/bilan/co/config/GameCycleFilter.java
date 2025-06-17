package org.bilan.co.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bilan.co.application.game.IGameCycleService;
import org.bilan.co.domain.dtos.game.GameCycleDto;
import org.bilan.co.domain.enums.GameCycleStatus;
import org.bilan.co.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class GameCycleFilter extends OncePerRequestFilter {

    @Autowired
    private IGameCycleService gameCycleService;

    @Autowired
    private ApplicationContext context;

    private GameCycleDto currentCycle;

    @PostConstruct
    public void init() {
        currentCycle = gameCycleService.getCurrentCycle().getResult();
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Allow if user has a specific role
        if (currentCycle.getGameStatus() == GameCycleStatus.ProcessingClosing && auth != null && auth.isAuthenticated()) {
            boolean isPrivileged = auth.getAuthorities().stream()
                    .anyMatch(granted -> granted.getAuthority().equals(Constants.ADMIN));

            if (isPrivileged) {
                filterChain.doFilter(request, response);
                return;
            }

            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "System is under maintenance");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
