package com.arcbees.facebook.client.domain;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Date;

/**
 * @see <a href="https://developers.facebook.com/docs/reference/api/user/">User</a>
 */
public class User extends JavaScriptObject {
    protected User() {
    }

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getFirstName() /*-{
        return this.first_name;
    }-*/;

    public final native String getMiddleName() /*-{
        return this.middle_name;
    }-*/;

    public final native String getLastName() /*-{
        return this.last_name;
    }-*/;

    /**
     * The user's gender: female or male
     */
    public final native Gender getGender() /*-{
        return @com.arcbees.facebook.client.domain.Gender::getGender(Ljava/lang/String;)(this.gender);
    }-*/;

    public final native String getLocale() /*-{
        return this.locale;
    }-*/;

    public final native BasicObject[] getLanguages() /*-{
        return this.languages;
    }-*/;

    /**
     * The URL of the profile for the user on Facebook
     */
    public final native String getLink() /*-{
        return this.link;
    }-*/;

    /**
     * An anonymous, but unique identifier for the user; only returned if specifically requested via the fields URL
     * parameter
     */
    public final native String getThirdPartyId() /*-{
        return this.third_party_id;
    }-*/;

    /**
     * Specifies whether the user has installed the application associated with the app access token that is used to
     * make the request; only returned if specifically requested via the fields URL parameter
     */
    public final native boolean getInstalled() /*-{
        if (this.installed) {
            return true;
        }

        return false;
    }-*/;

    public final native float getTimezone() /*-{
        return this.timezone;
    }-*/;

    /**
     * The last time the user's profile was updated; changes to the languages, link, timezone, verified,
     * interested_in, favorite_athletes, favorite_teams, and video_upload_limits are not not reflected in this value
     */
    public final native String getUpdatedTime() /*-{
        return this.updated_time;
    }-*/;

    public final native boolean isVerified() /*-{
        return this.verified;
    }-*/;

    public final native String getBio() /*-{
        return this.bio;
    }-*/;

    public final Date getBirthday() {
        return new Date(Date.parse(getBirthdayRepresentation()));
    }

    // TODO: Get the real object out of this structure.
    public final native String getEducation() /*-{
        return this.education;
    }-*/;

    public final native String getEmail() /*-{
        return this.email;
    }-*/;

    public final native BasicObject getHometown() /*-{
        return this.hometown;
    }-*/;

    public final native String[] getInterestedIn() /*-{
        return this.interested_in;
    }-*/;

    public final native BasicObject getLocation() /*-{
        return this.location;
    }-*/;

    public final native String getPolitical() /*-{
        return this.political;
    }-*/;

    public final native String getQuotes() /*-{
        return this.quotes;
    }-*/;

    /**
     * The user's relationship status: Single, In a relationship, Engaged, Married, It's complicated,
     * In an open relationship, Widowed, Separated, Divorced, In a civil union, In a domestic partnership
     */
    public final native RelationshipStatus relationshipStatus() /*-{
        return @com.arcbees.facebook.client.domain.RelationshipStatus::getRelationshipStatus(Ljava/lang/String;)(this.relationship_status);
    }-*/;

    public final native String getReligion() /*-{
        return this.religion;
    }-*/;

    public final native BasicObject getSignificantOther() /*-{
        return this.significant_other;
    }-*/;

    /**
     * The size of the video file and the length of the video that a user can upload; only returned if specifically
     * requested via the fields URL parameter
     */
    public final native VideoLimit getVideoUploadLimits() /*-{
        return this.video_upload_limits;
    }-*/;

    public final native String getWebsite() /*-{
        return this.website;
    }-*/;

    public final native Work[] getWork() /*-{
        return this.work;
    }-*/;

    private final native String getBirthdayRepresentation() /*-{
        return this.birthday;
    }-*/;
}
