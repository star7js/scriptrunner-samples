package com.adaptavist

import com.onresolve.scriptrunner.runner.rest.common.CustomEndpointDelegate
import groovy.json.JsonBuilder
import groovy.transform.BaseScript

import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import java.time.LocalTime

@BaseScript CustomEndpointDelegate delegate

currentTime(httpMethod: "GET", groups: ["jira-administrators"]) { MultivaluedMap queryParams, String body ->
    def now = LocalTime.now().format("HH:mm:ss")
    return Response.ok(new JsonBuilder([currentTime: now]).toString()).build();
}
