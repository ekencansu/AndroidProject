package com.example.cansu.Utility;

public interface FetchFromServerUser {
    void onPreFetch();
    void onFetchCompletion(String string, int id);
}