package com.example.bikproject.fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bikproject.R;
import com.example.bikproject.activities.DetailsActivity;
import com.example.bikproject.adapters.ItemsAdapter;
import com.example.bikproject.models.adapters.ItemsData;
import com.example.bikproject.models.enums.ProductEnum;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;


public class BonusFragment extends Fragment {
    public static final String COLLECTION_PATH = "product";
    public static final String PRODUCTS = "products/";
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    ItemsAdapter itemsAdapter;
    Map<String, Object> productData;


    public BonusFragment() {
    }


    public static BonusFragment newInstance() {
        BonusFragment fragment = new BonusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<ItemsData> dataArrayList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_bonus, container, false);
        Context context = container.getContext();
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseApp.initializeApp(context);
        firestore = FirebaseFirestore.getInstance();
        CollectionReference productRef = firestore.collection(COLLECTION_PATH);
        productRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot result = task.getResult();
                for (DocumentSnapshot r : result.getDocuments()) {
                    ItemsData itemsData = new ItemsData();
                    productData = r.getData();
                    itemsData.setName(getText(ProductEnum.NAME));
                    itemsData.setPrice(Integer.parseInt(getText(ProductEnum.PRICE)));
                    String photoName = getText(ProductEnum.PHOTO_NAME);
                    StorageReference child = storageRef.child(PRODUCTS + photoName);
                    child.getDownloadUrl().addOnCompleteListener(uriTask -> {
                        if (uriTask.isSuccessful()) {
                            itemsData.setImage(uriTask.getResult());
                        }
                    });
                    dataArrayList.add(itemsData);
                }
                itemsAdapter = new ItemsAdapter(context, dataArrayList);
            } else {
                Exception exception = task.getException();
                Log.e("[HOME]", exception.getMessage());
            }
        });


        return view;
    }

    private String getText(ProductEnum productEnum) {
        return productData.get(productEnum.getField()).toString();
    }
}


