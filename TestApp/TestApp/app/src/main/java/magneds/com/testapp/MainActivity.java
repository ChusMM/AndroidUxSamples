package magneds.com.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<String> mRecyclerItems = new ArrayList<>();

    protected RecyclerView mRecyclerView;
    protected RecyclerAdapter mRecyclerAdapter;

    public interface OnClickItem {
        void onClick(View view, int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonList personList = getPersonList();

        mRecyclerView = (RecyclerView) findViewById(R.id.home_recycler);
        mRecyclerAdapter = new RecyclerAdapter(personList);

        mRecyclerItems = new ArrayList<>();

        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private PersonList getPersonList() {
        String json = "{" + Utils.loadJSONFromAsset(getBaseContext()) + "}";
        return PersonList.fromJson(json);
    }

    protected void bindItemViewHolder(ViewHolder viewHolder, PersonList.Person person) {
        viewHolder.mNameTextView.setText(person.firstName);
    }

    protected void startDetailForPerson(PersonList.Person person) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.AGE_EXTRA, person.age);
        startActivity(intent);
    }

    protected class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnClickItem {
        private static final String TAG = "RecyclerAdapter";

        private PersonList personList;

        RecyclerAdapter(PersonList personList) {
            this.personList = personList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;

            View recycerItem =  LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.home_recycler_item, parent, false);
            viewHolder = new ViewHolder(recycerItem, this);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindItemViewHolder((ViewHolder) holder, this.personList.persons.get(position));
        }

        @Override
        public int getItemCount() {
            return personList.persons.size();
        }

        @Override
        public void onClick(View view, int position) {
            Log.i(TAG, "pos clicked: " + position);

            int id = view.getId();
            if (id == R.id.home_recyler_item_name) {
                try {
                    startDetailForPerson(personList.persons.get(position));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();

                    Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;

        ViewHolder(final View itemView, final OnClickItem listener) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.home_recyler_item_name);
            mNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, getAdapterPosition());
                }
            });
        }

        public void setName(String name){
            this.mNameTextView.setText(name);
        }
    }
}
