package bg.uni.sofia.fmi.mjt.Spotify;

import bg.uni.sofia.fmi.mjt.Spotify.account.Account;
import bg.uni.sofia.fmi.mjt.Spotify.account.AccountType;
import bg.uni.sofia.fmi.mjt.Spotify.exceptions.AccountNotFoundException;
import bg.uni.sofia.fmi.mjt.Spotify.exceptions.PlayableNotFoundException;
import bg.uni.sofia.fmi.mjt.Spotify.exceptions.PlaylistCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Spotify.exceptions.StreamingServiceException;
import bg.uni.sofia.fmi.mjt.Spotify.playable.Playable;
import bg.uni.sofia.fmi.mjt.Spotify.playlist.Playlist;

public class Spotify implements StreamingService{

    Account[] accounts;
    Playable[] playableContent;

    public Spotify(Account[] accounts, Playable[] playableContent){
        this.accounts = accounts;
        this.playableContent = playableContent;
    }

    @Override
    public void play(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException {
        validateNotNull(account);
        validateNotNull(title);
        validateNotEmpty(title);

        Account acc = this.findAccount(account);
        Playable playable = this.findByTitle(title);

        acc.listen(playable);
    }

    @Override
    public void like(Account account, String title) throws AccountNotFoundException, PlayableNotFoundException, StreamingServiceException {
        validateNotNull(account);
        validateNotNull(title);
        validateNotEmpty(title);

        Account acc = this.findAccount(account);
        Playable playable = this.findByTitle(title);

        try{
            acc.like(playable);
        } catch (PlaylistCapacityExceededException e){
            throw new StreamingServiceException(e.getMessage());
        }
    }

    @Override
    public Playable findByTitle(String title) throws PlayableNotFoundException {
        validateNotNull(title);
        validateNotEmpty(title);
        for(Playable playable : playableContent){
            if(playable.getTitle().equals(title)){
                return playable;
            }
        }
        throw new PlayableNotFoundException("Playable not found");
    }

    public Account findAccount(Account account) throws AccountNotFoundException {
        validateNotNull(account);
        for(Account _account: accounts){
            if(_account == account){
                return _account;
            }
        }
        throw new AccountNotFoundException("Account not found");
    }
    @Override
    public Playable getMostPlayed() {
        Playable mostPlayed = null;
        int maxPlays = -1;
        for(Playable playable : playableContent){
            if(playable.getTotalPlays() > maxPlays){
                maxPlays = playable.getTotalPlays();
                mostPlayed = playable;
            }
        }
        return mostPlayed;
    }

    @Override
    public double getTotalListenTime() {
        double listenTime = 0.0;
        for(Account account : accounts){
            listenTime += account.getTotalListenTime();
        }
        return listenTime;
    }

    @Override
    public double getTotalPlatformRevenue() {
        double totalPlatformRevenue = 0.0;
        for(Account account:accounts){
            totalPlatformRevenue += account.getRevenue();
        }
        return totalPlatformRevenue;
    }

    private void validateNotNull(Object o) {
        if(o == null){
            throw new IllegalArgumentException("null obj");
        }
    }

    private void validateNotEmpty(String s) {
        if(s.isEmpty()){
            throw new IllegalArgumentException("null obj");
        }
    }
}
