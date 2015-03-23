package com.perso.fab.teammanager.interfaces;

public interface OnFragmentInteractionListener {
    /**
     * Called when user select an item in the main lists (GAMES/PLAYERS/TEAMS
     * @param position : the index of the selected item
     * @param kind : GAME/PLAYER/TEAM
     */
    void onMainListItemSelected(int position, int kind);
}
