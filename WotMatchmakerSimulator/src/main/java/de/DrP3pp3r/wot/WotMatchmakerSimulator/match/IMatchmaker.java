package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.API;

public interface IMatchmaker {
void setApi(API api);
void start();
void stop();
}
