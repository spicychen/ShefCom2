This program is written based on eSheet6. And I have added some files and changed loads of things in the original file.
The files I added:
Hand.java
Lamp.java
Room.java
TruncatedCone.java
fs_scenary_05.txt
fs_wd_05.txt

The files I changed:
Arty.java (was M04.java)
Arty_GLEventListener.java (was M04_GLEventListener.java)
Cube.java
fs_cube_04.txt
fs_light_01.txt
fs_tt_05.txt
Light.java


the robot arm is defined in Hand.java. it consist of a sphere as arm, cubes as paml and fingers and cylinder as ring. the size of the hand is relative, you can set the size by change the size value. fingers and palm have creeper body texture as diffuse texture and they have the creeper head texture as specular texture. so the specular of the finger and cylinder will be creeper head. make sure you are prepared so you don't get scared when you see the shiny bit of the hand. the thumb is set to darker color which will make the specular more obvious.
in the code, all fingers and palm and arm are hierachied using the SGNode. an example is arm -> palm -> thumb root -> thumb body -> thumb head. transformation nodes are added to the hierachy as well so that it can animate.

the animation function of the hand takes following parameter: id of the hand part, the current elapsed time, startTime, endTime, target angle that you want the mesh rotate, direction of the rotation (XYZ), the continue Time from which the animation is continued, and the speed of the rotation. the default period of one complete movement (finger moves to destination and moves back) is 2 second unless you change the speed of the animation.
for example you can using this function to make the thumb rotate 60 in Z direction and then rotate 90 in Y direction after the rotation of the index finger has elapsed 0.5 second.

in this way I made the robot arm perform a 'J' ASL in dozen lines. And the animation shows the 3 Characters of my last name 'J', 'U', 'N', follow by a gesture of 'I Love You'. the time is calculated so that it has smooth animation. Their can all be seen using the corresponding buttons. However, 'J' in ASL is a animate gesture, the static position of 'J' might make you think it is 'I'.

the room is consist of some texture mapped two triangles. They may use the different shader to render. e.g. walls and pictures are using the original shader, where east wall use the window mode shader where you can define the position of the window so that it is transparent and you can see the outside scenary. The scenary uses another shader so that it won't be affected by the room lights. And it is animated, for where you said there would be extra mark.

The ring is added in the ring finger with an amazing spotlight. It is modelled using cylinder that was a special case of TruncatedCone. The spotlight will have an spot diffuse in its spot direction in the room. it will change the direction according to the ring. I also add two buttons in GUI that can make the spotlight have flash effect. they are 'position flash' and 'colour flash'.

General world light is added on the top of the room. A lamp is also added and positioned at the corner of the room. Both lights have their corresponding switch button. They are initially switched on, when turned off, the spotlight effect is more obvious.

The camera is using the original sheet. you can go around the room and see the scenary in different angle, while switching the lights and animate the hand.

Buttons are added to the GUI to control the animation. start, stop and reset.

There is a video in the following link
https://www.useloom.com/share/f5776c07147749de8422cdcd90ac921b
please forgive my poor quality of description.