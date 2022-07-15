package bg.uni.sofia.fmi.mjt.Labs.Spotify;

import bg.uni.sofia.fmi.mjt.Labs.Spotify.Spotify;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.StreamingService;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.account.Account;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.account.FreeAccount;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.account.PremiumAccount;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.AccountNotFoundException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.PlayableNotFoundException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.StreamingServiceException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playable.Audio;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playable.Playable;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playable.Video;

public class main {
    public static void main(String[] args) throws PlayableNotFoundException, StreamingServiceException, AccountNotFoundException {
        Account account = new FreeAccount("ac1@abv.bg");
        Account account1 = new PremiumAccount("2a@gmail.com");
        Account account2 = new FreeAccount("555@tmp.org");

        Playable p = new Audio("Angel eyes","Abba", 1972, 2.45);
        Playable p1 = new Video("Sticky","Drake", 2022, 3.18);
        Playable p2 = new Audio("Hollywood's bleeding","Post Malone", 2019, 3.5);
        Playable p3 = new Video("Calling my name","Drake", 2018, 2.45);
        Playable p4 = new Audio("Circles","Post Malone", 2019, 2.58);
        Playable p5 = new Video("Take what you want from me","Post Malone", 2019, 3.21);

        StreamingService spotify = new Spotify(new Account[]{account,account1,account2}, new Playable[]{p,p1,p2,p3,p4,p5});

        Playable testP = spotify.findByTitle("Sticky");
        spotify.like(account, "Circles");

        spotify.play(account2, "Take what you want from me");
        spotify.play(account1, "Sticky");
        spotify.play(account, "Take what you want from me");

        System.out.println(spotify.getTotalListenTime());

    }
}
