/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.website.assets.client.resource.forkOnGithub2;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ForkOnGithubResource extends ClientBundle {
    public interface ForkOnGithub_style extends CssResource {
        String forkOnGithub();

        String img();
    }

    ForkOnGithub_style forkOnGithub_style();

    public interface ForkOnGithub_mediascreen extends CssResource {
        String forkOnGithub();

        String img();

        String logo();

        String websiteWrapper();
    }

    ForkOnGithub_mediascreen forkOnGithub_mediascreen();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Both)
    ImageResource githubDashedLine();

    ImageResource githubForkMe();

    ImageResource githubForkMe_fr();

    ImageResource githubLogo();

    ImageResource githubLogo_dn();
}
