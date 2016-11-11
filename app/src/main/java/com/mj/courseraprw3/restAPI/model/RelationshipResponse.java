package com.mj.courseraprw3.restAPI.model;

/**
 * Created by leyenda on 11/8/16.
 */

public class RelationshipResponse {
    public String outgoing_status;
    public String target_user_is_private;
    public String incoming_status;

    public void setRelationshipResponse(RelationshipResponse relationshipResponse)
    {
        this.setOutgoing_status(relationshipResponse.getOutgoing_status());
        this.setTarget_user_is_private(relationshipResponse.getTarget_user_is_private());
        this.setIncoming_status(relationshipResponse.getIncoming_status());
    }

    public RelationshipResponse(String outgoing_status, String target_user_is_private, String incoming_status) {

        this.outgoing_status = outgoing_status;
        this.target_user_is_private = target_user_is_private;
        this.incoming_status = incoming_status;
    }

    public RelationshipResponse() {
    }

    public String getOutgoing_status() {
        return outgoing_status;
    }

    public void setOutgoing_status(String outgoing_status) {
        this.outgoing_status = outgoing_status;
    }

    public String getTarget_user_is_private() {
        return target_user_is_private;
    }

    public void setTarget_user_is_private(String target_user_is_private) {
        this.target_user_is_private = target_user_is_private;
    }

    public String getIncoming_status() {
        return incoming_status;
    }

    public void setIncoming_status(String incoming_status) {
        this.incoming_status = incoming_status;
    }
}
