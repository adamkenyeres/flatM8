package util;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Sets;
import model.chat.ChatContact;
import model.chat.ChatMessage;
import model.criteria.BaseCriteria;
import model.criteria.GenderCriteria;
import model.criteria.LifestyleCriteria;
import model.criteria.RoomTypeCriteria;
import model.flat.Address;
import model.flat.Flat;
import model.flat.FlatType;
import model.flatmate.FlatMateEntry;
import model.flatmate.RoomCriteria;
import model.request.AddMateRequest;
import model.request.DeleteFlatRequest;
import model.request.DeleteMateRequest;
import model.request.RequestStatus;
import model.tenant.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DummyDataGenerator {

    public static User generateDummyUser() {
        User u = new User();
        u.setEmail("test@test.hu");
        u.setFirstName("Test");
        u.setLastName("Janos");
        return u;
    }

    public static Address generateDummyAddress() {
        Address address = new Address();
        address.setCity("Budapest");
        address.setDistrict(9);
        address.setStreetAddress("Balazs Bela utca");
        address.setStreetNumber(45);
        address.setFloor(4);
        address.setDoor(1);
        return address;
    }

    public static Flat generateDummyFlat() {
        Flat flat = new Flat();
        flat.setAddress(generateDummyAddress());
        flat.setCapacity(FLAT_CAPACITY);
        flat.setFlatType(FlatType.FLAT);
        flat.setUserEmail("test@test.hu");
        flat.setRoomCount(FLAT_ROOMCOUNT);
        flat.setFlatMates(Lists.newArrayList(generateDummyUser()));
        return flat;
    }

    public static ChatMessage generateDummyChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatContact(generateDummyChatContact());
        chatMessage.setMessage("Test");
        chatMessage.setReceivers(Sets.newHashSet(generateDummyUser()));
        chatMessage.setSender(generateDummyUser());
        chatMessage.setTimestamp(new Date());
        return chatMessage;
    }

    public static ChatContact generateDummyChatContact() {
        ChatContact chatContact = new ChatContact();
        chatContact.setContactEntry(generateDummyFlatMateEntry());
        chatContact.setSenderEmail("test@test.hu");
        return chatContact;
    }

    public static FlatMateEntry generateDummyFlatMateEntry() {
        FlatMateEntry flatMateEntry = new FlatMateEntry();
        flatMateEntry.setFlat(generateDummyFlat());
        flatMateEntry.setPhotos(Collections.emptyList());
        flatMateEntry.setRoomCriteria(generateDummyRoomCriteria());
        return flatMateEntry;
    }

    public static RoomCriteria generateDummyRoomCriteria() {
        RoomCriteria roomCriteria = new RoomCriteria();
        roomCriteria.setAdditionalDetails(Collections.emptyList());
        roomCriteria.setCapacity(ROOM_SIZE);
        roomCriteria.setCriteria(generateDummyBaseCriteria());
        roomCriteria.setSize(ROOM_CAPACITY);
        return roomCriteria;
    }

    public static BaseCriteria generateDummyBaseCriteria() {
        BaseCriteria baseCriteria = new BaseCriteria();
        baseCriteria.setAgeCriteria(AGE_CRITERIA);
        baseCriteria.setAgeOffset(AGE_OFFSET);
        baseCriteria.setGenderCriteria(GENDER_CRITERIA);
        baseCriteria.setRoomTypeCriteria(ROOM_CRITERIA);
        baseCriteria.setLifestyleCriteria(LIFESTYLE_CRITERIA);
        return baseCriteria;
    }

    public static AddMateRequest generateDummyAddMateRequest() {
        AddMateRequest addMateRequest = new AddMateRequest();
        addMateRequest.setMateToAdd(generateDummyUser());
        addMateRequest.setRequestStatus(RequestStatus.PENDING);
        addMateRequest.setFlat(generateDummyFlat());
        addMateRequest.setSender(generateDummyUser());
        addMateRequest.setReceivers(Lists.newArrayList(generateDummyUser()));
        addMateRequest.setId("XXX");
        return addMateRequest;
    }

    public static DeleteFlatRequest generateDeleteFlatRequest() {
        DeleteFlatRequest deleteFlatRequest = new DeleteFlatRequest();
        deleteFlatRequest.setId("XXX");
        return deleteFlatRequest;
    }

    public static DeleteMateRequest generateDeleteMateRequest() {
        DeleteMateRequest deleteMateRequest = new DeleteMateRequest();
        deleteMateRequest.setId("XXX");
        return deleteMateRequest;
    }

    public static final Integer AGE_CRITERIA = 20;
    public static final Integer AGE_OFFSET = 3;
    public static final Integer AGE_IN_RADIUS = AGE_CRITERIA + AGE_OFFSET - 1;
    public static final Integer AGE_OUT_OF_RADIUS = AGE_CRITERIA + AGE_OFFSET + 1;
    public static final Integer ROOM_SIZE = 50;
    public static final Integer ROOM_CAPACITY = 1;
    public static final Integer FLAT_CAPACITY = 2;
    public static final Integer FLAT_ROOMCOUNT = 2;
    public static final LifestyleCriteria LIFESTYLE_CRITERIA = LifestyleCriteria.WORK;
    public static final GenderCriteria GENDER_CRITERIA = GenderCriteria.FEMALE;
    public static final RoomTypeCriteria ROOM_CRITERIA = RoomTypeCriteria.PRIVATE_ROOM;
}
