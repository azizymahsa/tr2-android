package ir.trap.tractor.android.apiServices.model.getHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHistory
{
        @SerializedName("images")
        @Expose
        private List<Image> images = null;
        @SerializedName("history")
        @Expose
        private List<History> history = null;
        @SerializedName("players_current")
        @Expose
        private List<PlayersCurrent> playersCurrent = null;
        @SerializedName("players")
        @Expose
        private List<Player> players = null;

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public List<History> getHistory() {
            return history;
        }

        public void setHistory(List<History> history) {
            this.history = history;
        }

        public List<PlayersCurrent> getPlayersCurrent() {
            return playersCurrent;
        }

        public void setPlayersCurrent(List<PlayersCurrent> playersCurrent) {
            this.playersCurrent = playersCurrent;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public void setPlayers(List<Player> players) {
            this.players = players;
        }

}

