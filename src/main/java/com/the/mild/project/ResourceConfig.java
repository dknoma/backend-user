package com.the.mild.project;

import static com.the.mild.project.ResourceConfig.CommonPaths.PATH_CREATE;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_UPDATE_BY_ID;

public final class ResourceConfig {
    public static final String SERVICE_NAME = "user";


    // Resource path names
    public static final String PATH_USER_RESOURCE = "user";
    public static final String PATH_USER_RESOURCE_CREATE = PATH_USER_RESOURCE + PATH_CREATE;
    public static final String PATH_USER_RESOURCE_UPDATE = PATH_USER_RESOURCE + PATH_PARAM_UPDATE_BY_ID;
    public static final String PATH_USER_RESOURCE_UPDATE_FORMAT = "user/%s/update";

    public static final class PathFormats {
        public static final String PATH_TEST_RESOURCE_WITH_PARAM_FORMAT = "/%s";
        public static final String PATH_TEST_RESOURCE_WITH_MULTIPLE_PARAMS_FORMAT = "/%s/example/%s";

        private PathFormats() {
            // Utility
        }
    }

    public static final class CommonPaths {
        public static final String PATH_CREATE = "/create";

        private CommonPaths() {
            // Utility
        }
    }

    public static final class PathParams {
        public static final String PATH_PARAM_ID = "id";
        public static final String PATH_PARAM_UPDATE_BY_ID = "/{" + PATH_PARAM_ID + "}/update";

        private PathParams() {
            // Utility
        }
    }

    private ResourceConfig() {
        // Utility
    }
}
