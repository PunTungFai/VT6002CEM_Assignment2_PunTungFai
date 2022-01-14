package com.example.vt6002cem_assignment2.ArCore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.product.Product;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;

public class ArcoreActivity extends AppCompatActivity {
    private Product product;
    private ArFragment arFragment;
    private Button btnRemove;
    private AnchorNode anchorNode;

    private ArrayList<String> imagesPath = new ArrayList<>();
    private ArrayList<String> namesPath = new ArrayList<>();
    private ArrayList<String> modelNames = new ArrayList<>();


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcore);


        Intent i = getIntent();
        //Intent get data
        product = (Product) i.getSerializableExtra("ProductAR");
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        btnRemove = findViewById(R.id.remove);
        getImages();


        //set arFragment and call google Arcore api
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            Anchor anchor = hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(this,
                            RenderableSource.builder().setSource(
                                    this,
                                    Uri.parse(Common.model),          //Uri 3d link
                                    RenderableSource.SourceType.GLB) //Support 3dmodel glb
                                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                    .build())
                    .setRegistryId(Common.model)
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable));

        });


        btnRemove.setOnClickListener(view -> removeAnchorNode(anchorNode));



    }

    private void getImages() {

        imagesPath.add(product.getImg());
        namesPath.add(product.getProductname());
        modelNames.add(product.getArlink());






        initaiteRecyclerview();
    }

    private void initaiteRecyclerview() {

        //Arraylist
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this,namesPath,imagesPath,modelNames);
        recyclerView.setAdapter(adapter);

    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

        //set size
        if(product.getProducttype().matches("Toys")){
            node.getScaleController().setMinScale(0.4999f);
            node.getScaleController().setMaxScale(0.5f);
        }else{
            node.getScaleController().setMinScale(0.8999f);
            node.getScaleController().setMaxScale(0.9f);
            System.out.println();
        }



        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public void removeAnchorNode(AnchorNode nodeToremove) {
        if (nodeToremove != null) {
            arFragment.getArSceneView().getScene().removeChild(nodeToremove);
            nodeToremove.getAnchor().detach();
            nodeToremove.setParent(null);
            nodeToremove = null;
        }
    }


    //Back button
    public void backbutton(View view) {
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}