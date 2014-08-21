package io.lumify.web.routes.user;

import io.lumify.core.config.Configuration;
import io.lumify.core.model.user.UserRepository;
import io.lumify.core.model.workspace.WorkspaceRepository;
import io.lumify.core.user.User;
import io.lumify.web.BaseRequestHandler;
import io.lumify.miniweb.HandlerChain;
import com.google.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfo extends BaseRequestHandler {
    @Inject
    public UserInfo(
            final UserRepository userRepository,
            final WorkspaceRepository workspaceRepository,
            final Configuration configuration) {
        super(userRepository, workspaceRepository, configuration);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, HandlerChain chain) throws Exception {
        String[] userIds = getRequiredParameterArray(request, "userIds[]");

        JSONObject result = new JSONObject();
        JSONObject users = new JSONObject();
        result.put("users", users);

        for (String userId : userIds) {
            User user = getUserRepository().findById(userId);
            JSONObject userJson = UserRepository.toJson(user);
            users.put(user.getUserId(), userJson);
        }

        respondWithJson(response, result);
    }
}
