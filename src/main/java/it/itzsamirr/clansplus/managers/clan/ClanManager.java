package it.itzsamirr.clansplus.managers.clan;

import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.clan.Clan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ClanManager extends Manager {
    void removeClan(Clan clan);
    Clan addClan(String name, UUID leader);
    Clan getClan(String name);
    Clan getClan(UUID player);
    ArrayList<Clan> getClans();
    void save();
    void load();
}
