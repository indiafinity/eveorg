package com.example.eveorg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eveorg.Model.Events;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class fragment_home extends Fragment{

    public static final String TAG = "fragment_home";
    RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<Events> eventsArrayList;
    RecyclerViewAdapter adapter;
    String UID;

    //ArrayList<Events> new_list;
    //ListView listView;
    //CardView cardView1,cardView2, cardView3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        eventsArrayList = new ArrayList<>();


        final View view = inflater.inflate(R.layout.fragment_home,container,false);

        //listView = view.findViewById(R.id.list12);

        //ArrayAdapter<String> adapt = new ArrayAdapter<>(getContext(), R.layout.view_cardview);
        //listView.setAdapter(adapt);

        //cardView1 = view.findViewById(R.id.cardView1);
        //cardView2 = view.findViewById(R.id.cardView2);
        //cardView3 = view.findViewById(R.id.cardView3);
        /*
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"On Click 1", Toast.LENGTH_SHORT).show();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"On Click 2", Toast.LENGTH_SHORT).show();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"On Click 3", Toast.LENGTH_SHORT).show();
            }
        });
*/


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapter =  new RecyclerViewAdapter(eventsArrayList)
        adapter = new RecyclerViewAdapter(eventsArrayList);
        recyclerView.setAdapter(adapter);


//        eventsArrayList.add(new Events("New Event"));
        //recyclerView.setAdapter(new RecyclerViewAdapter(eventsArrayList));

        db.collection("new_events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){

                    Log.i("ERROR: ",e.toString());
                    return;
                }
                //DocumentReference doc_ref = (DocumentReference) Objects.requireNonNull(queryDocumentSnapshots).getDocuments();
                //Log.i(TAG,"Doc ref is "+doc_ref);



                eventsArrayList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    String img = Objects.requireNonNull(doc.get("img")).toString();
                    String name = Objects.requireNonNull(doc.get("name")).toString();
                    //String details = Objects.requireNonNull(doc.get("detail")).toString();
                    //Log.i(TAG,doc.get("name").toString());
                    eventsArrayList.add(new Events(img,name));

                    //Log.i(TAG,String.valueOf(eventsArrayList.size()));
                }
                //Log.i(TAG,String.valueOf(eventsArrayList.size()));
                adapter.notifyDataSetChanged();

            }

        });
        adapter.addAll(eventsArrayList);
/*
        db.collection("new_events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot: Objects.requireNonNull(task.getResult())) {
                            String img = Objects.requireNonNull(documentSnapshot.get("img")).toString();
                            String name = Objects.requireNonNull(documentSnapshot.get("name")).toString();
                            eventsArrayList.add(new Events(img,name));
                            //eventsArrayList.add(new Events(documentSnapshot.get("img").toString(),
                                    //documentSnapshot.get("name")).toString());
                            //recyclerView.getAdapter();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Database Error!!",Toast.LENGTH_SHORT).show();
                    }
                });
*/
//        adapter.addAll(eventsArrayList);

        return view;

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView desc, read;
        ImageView image;
        CardView cardView;

        public RecyclerViewHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
            super(inflater.inflate(R.layout.view_cardview,container,false));

            desc = itemView.findViewById(R.id.textView);
            read = itemView.findViewById(R.id.readMore);
            image = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        ArrayList<Events> eventsArrayList;

        public RecyclerViewAdapter(ArrayList<Events> userArrayList) {
            this.eventsArrayList = userArrayList;
            //eventsArrayList.add(new Events("image view","SHPC"));
        }

        public void addAll(List<Events> ArrayList) {
            eventsArrayList.addAll(ArrayList);
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //View view = layoutInflater.inflate(R.layout.view_cardview,viewGroup,false);

            //imageView = view.findViewById(R.id.imageView);

            return new RecyclerViewHolder(layoutInflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {

            Glide.with(getContext()).load(eventsArrayList.get(i).getImage()).into(recyclerViewHolder.image);
            final String url = eventsArrayList.get(i).getImage();
            //final String des = eventsArrayList.get(i).getDetail();

            //recyclerViewHolder.image.setImageURI(Uri.parse(eventsArrayList.get(i).getImage()));

            recyclerViewHolder.desc.setText(eventsArrayList.get(i).getName());
            recyclerViewHolder.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("new_events")
                            .whereEqualTo("name",recyclerViewHolder.desc.getText())
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                            @javax.annotation.Nullable FirebaseFirestoreException e) {

                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                        String abc = doc.getId();
                                        Intent nextIntent = new Intent(getActivity(),Event_Detail.class);
                                        nextIntent.putExtra("title",recyclerViewHolder.desc.getText());
                                        nextIntent.putExtra("image",url);
                                        nextIntent.putExtra("documentUID", abc);
                                        //nextIntent.putExtra("description",des);
                                        //nextIntent.putExtra("UID",UID);
                                        startActivity(nextIntent);
                                    }

                                }
                            });


                }
            });

        }

        @Override
        public int getItemCount() {
            //return 5;
            return eventsArrayList.size();
        }
    }

}
