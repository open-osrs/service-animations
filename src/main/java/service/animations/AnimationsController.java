/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package service.animations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/animations")
public class AnimationsController {
    public static HashMap<Integer, int[]> animations = new HashMap<>();
    private Gson backupWriter = new GsonBuilder().setPrettyPrinting().create();

    @RequestMapping("/get")
    public HashMap<Integer, int[]> get() {
        return animations;
    }

    @RequestMapping("/submit")
    public void submitRegion(@RequestParam int npcid, int animation) {
        if (animations.get(npcid)!=null)
        {
            for (int i : animations.get(npcid))
            {
                if (i == animation)
                {
                    return;
                }
            }
        }
        int[] newAnimations = ArrayUtils.add(animations.get(npcid), animation);
        animations.put(npcid, newAnimations);
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    private void backupSounds()
    {
        try (FileWriter writer = new FileWriter(new File("./animations.json")))
        {
            writer.write(backupWriter.toJson(animations));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
