package com.megift;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

	@Override
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
		Logger.error("Bad Request uri: " + request.uri());
		Logger.error("Bad Request Error: " + error);
		return Promise.<Result> pure(badRequest("Don't try to hack the URI!"));
	}

	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		Logger.error("Not found Page: " + request.uri());
		return Promise.<Result> pure(notFound(views.html.resources.notFoundPage.render()));
	}

	@Override
	public Promise<Result> onError(RequestHeader request, Throwable t) {
		Logger.error(t.getMessage(), t);
		return Promise.<Result> pure(internalServerError(views.html.resources.errorPage.render()));
	}

}