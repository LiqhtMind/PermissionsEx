/**
 * PermissionsEx
 * Copyright (C) zml and PermissionsEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ninja.leaping.permissionsex.sponge;

import ninja.leaping.permissionsex.data.CalculatedSubject;
import ninja.leaping.permissionsex.util.Translatable;
import ninja.leaping.permissionsex.util.command.MessageFormatter;
import ninja.leaping.permissionsex.util.command.Commander;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * An abstraction over the Sponge CommandSource that handles PEX-specific message formatting and localization
 */
public class SpongeCommander implements Commander<Text> {
    private final CommandSource commandSource;
    private final SpongeMessageFormatter formatter;

    public SpongeCommander(PermissionsExPlugin pex, CommandSource commandSource) {
        this.commandSource = commandSource;
        this.formatter = new SpongeMessageFormatter(pex, getLocale());
    }

    @Override
    public String getName() {
        return this.commandSource.getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSource.hasPermission(permission);
    }

    public Locale getLocale() {
        return commandSource instanceof Player ? ((Player) commandSource).getLocale() : Locale.getDefault();
    }

    @Override
    public Set<Map.Entry<String, String>> getActiveContexts() {
        return PEXOptionSubjectData.parSet(commandSource.getActiveContexts());
    }

    @Override
    public MessageFormatter<Text> fmt() {
        return formatter;
    }

    @Override
    public void msg(Translatable message, Object... args) {
        commandSource.sendMessage(
                Texts.builder(new SpongeMessageFormatter.FixedTranslation(message.translate(getLocale())), args)
                .color(TextColors.DARK_AQUA)
                .build());
    }


    @Override
    public void debug(Translatable message, Object... args) {
        commandSource.sendMessage(
                Texts.builder(new SpongeMessageFormatter.FixedTranslation(message.translate(getLocale())), args)
                        .color(TextColors.GRAY)
                        .build());
    }

    @Override
    public void error(Translatable message, Object... args) {
        commandSource.sendMessage(
                Texts.builder(new SpongeMessageFormatter.FixedTranslation(message.translate(getLocale())), args)
                        .color(TextColors.RED)
                        .build());
    }
}
